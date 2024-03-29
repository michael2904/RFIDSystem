package com.rfidsystem;

/**
 * Created by yordan on 3/22/17.
 */
// Import the API
import com.thingmagic.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReadAsync {

	static SerialPrinter serialPrinter
			;
	static StringPrinter stringPrinter;
	static TransportListener currentListener;

	static void usage() {
		System.out.printf("Usage: Please provide valid arguments, such as:\n"
				+ "  (URI: 'tmr:///COM1 --ant 1,2' or 'tmr://astra-2100d3/ --ant 1,2' "
				+ "or 'tmr:///dev/ttyS0 --ant 1,2')\n\n");
		System.exit(1);
	}

	public static void setTrace(Reader r, String args[]) {
		if (args[0].toLowerCase().equals("on")) {
			r.addTransportListener(Reader.simpleTransportListener);
			currentListener = Reader.simpleTransportListener;
		} else if (currentListener != null) {
			r.removeTransportListener(Reader.simpleTransportListener);
		}
	}

	static class SerialPrinter implements TransportListener {

		public void message(boolean tx, byte[] data, int timeout) {
			System.out.print(tx ? "Sending: " : "Received:");
			for (int i = 0; i < data.length; i++) {
				if (i > 0 && (i & 15) == 0) {
					System.out.printf("\n         ");
				}
				System.out.printf(" %02x", data[i]);
			}
			System.out.printf("\n");
		}
	}

	static class StringPrinter implements TransportListener {

		public void message(boolean tx, byte[] data, int timeout) {
			System.out.println((tx ? "Sending:\n" : "Receiving:\n")
					+ new String(data));
		}
	}

	public static void main(String argv[]) {
		new GeneralInfoScreen();


		System.out.println("HI");
		// Program setup
		Reader r = null;
		int nextarg = 0;
		boolean trace = false;
		int[] antennaList = null;

		if (argv.length < 1) {
			usage();
		}

		if (argv[nextarg].equals("-v")) {
			trace = true;
			nextarg++;
		}

		// Create Reader object, connecting to physical device
		try {
			String readerURI = argv[nextarg];
			nextarg++;

			for (; nextarg < argv.length; nextarg++) {
				String arg = argv[nextarg];
				if (arg.equalsIgnoreCase("--ant")) {
					if (antennaList != null) {
						System.out.println("Duplicate argument: --ant specified more than once");
						usage();
					}
					antennaList = parseAntennaList(argv, nextarg);
					nextarg++;
				} else {
					System.out.println("Argument " + argv[nextarg] + " is not recognised");
					usage();
				}
			}

			r = Reader.create(readerURI);
			if (trace) {
				setTrace(r, new String[]{"on"});
			}
			r.connect();
			if (Reader.Region.UNSPEC == (Reader.Region) r.paramGet("/reader/region/id")) {
				Reader.Region[] supportedRegions = (Reader.Region[]) r.paramGet(TMConstants.TMR_PARAM_REGION_SUPPORTEDREGIONS);
				if (supportedRegions.length < 1) {
					throw new Exception("Reader doesn't support any regions");
				} else {
					r.paramSet("/reader/region/id", supportedRegions[0]);
				}
			}

			String model = r.paramGet("/reader/version/model").toString();
			if ((model.equalsIgnoreCase("Sargas") || model.equalsIgnoreCase("M6e Micro") || model.equalsIgnoreCase("M6e Nano")) && antennaList == null) {
				System.out.println("Module doesn't has antenna detection support, please provide antenna list");
				r.destroy();
				usage();
			}

			// Set Custom Optimization Settings
			r.paramSet(TMConstants.TMR_PARAM_GEN2_TARI, Gen2.Tari.TARI_12_5US);
			r.paramSet(TMConstants.TMR_PARAM_GEN2_BLF,
					Gen2.LinkFrequency.LINK250KHZ);
			r.paramSet(TMConstants.TMR_PARAM_GEN2_TAGENCODING,
					Gen2.TagEncoding.M2);
			r.paramSet(TMConstants.TMR_PARAM_GEN2_Q, new Gen2.StaticQ(3));
			r.paramSet(TMConstants.TMR_PARAM_GEN2_SESSION, Gen2.Session.S1);
			r.paramSet(TMConstants.TMR_PARAM_GEN2_TARGET, Gen2.Target.A);

//            System.out.println("Tari: "
//                    + r.paramGet(TMConstants.TMR_PARAM_GEN2_TARI));
//            System.out.println("BLF: "
//                    + r.paramGet(TMConstants.TMR_PARAM_GEN2_BLF));
//            System.out.println("Tag Encoding: "
//                    + r.paramGet(TMConstants.TMR_PARAM_GEN2_TAGENCODING));
//            System.out.println("Q: "
//                    + r.paramGet(TMConstants.TMR_PARAM_GEN2_Q));
//            System.out.println("Session: "
//                    + r.paramGet(TMConstants.TMR_PARAM_GEN2_SESSION));
//            System.out.println("Target: "
//                    + r.paramGet(TMConstants.TMR_PARAM_GEN2_TARGET));
//            System.out.println("Tag Encoding: "
//                    + r.paramGet(TMConstants.TMR_PARAM_GEN2_TAGENCODING));

			SimpleReadPlan plan = new SimpleReadPlan(antennaList, TagProtocol.GEN2, true);
			r.paramSet(TMConstants.TMR_PARAM_READ_PLAN, plan);
			ReadExceptionListener exceptionListener = new TagReadExceptionReceiver();
			r.addReadExceptionListener(exceptionListener);
			// Create and add tag listener
			ReadListener rl = new PrintListener();
			r.addReadListener(rl);
			// search for tags in the background
			java.util.Date startTimeDate = new java.util.Date(System.currentTimeMillis());
			System.out.println("Reading for 90 seconds... starting at "+startTimeDate.toString());
			System.out.println("TagId,Time,ReadCount,Averaged RSSI");
			r.startReading();
			Boolean trueBool = true;
			while(trueBool){

			}
			r.stopReading();
			java.util.Date stopTimeDate = new java.util.Date(System.currentTimeMillis());
			System.out.println("Read for 90 seconds... stopped at "+stopTimeDate.toString());
			r.removeReadListener(rl);
			r.removeReadExceptionListener(exceptionListener);

			// Shut down reader
			r.destroy();
			System.exit(0);
		} catch (ReaderException re) {
			System.out.println("ReaderException: " + re.getMessage());
		} catch (Exception re) {
			System.out.println("Exception: " + re.getMessage());
		}
	}

	static class PrintListener implements ReadListener {

		public void tagRead(Reader r, TagReadData tr) {
//            java.util.Date startTimeDate = new java.util.Date(tr.getTime());
//            System.out.println("Tag ID: " + tr.getTag().epcString() +" Time: " + tr.getTime() + ", "+startTimeDate+" Frequency: " + tr.getFrequency() + " Antenna: " + tr.getAntenna() + " Read Count: " + tr.getReadCount() + "  RSSI: " + tr.getRssi());
//            if(tr.getTag().epcString().equals("300833B2DDD9014000000000")){
//                System.out.println("Tag ID: " + tr.getTag().epcString() +" Time: " + tr.getTime() + ", "+startTimeDate+" Frequency: " + tr.getFrequency() + " Antenna: " + tr.getAntenna() + " Read Count: " + tr.getReadCount() + "  RSSI: " + tr.getRssi());
//            }
			RFIDSystem rf = RFIDSystem.getInstance();
			Item item = null;
			if (!rf.checkItem(tr.getTag().epcString()) && tr.getTag().epcString().equals("300833B2DDD9014000000000")) {
				item = new Item(tr.getTag().epcString());
				rf.addItem(item);
				item.addData(tr);
//                System.out.println("Tag ID: " + tr.getTag().epcString() +" Time: " + tr.getTime() + " Frequency: " + tr.getFrequency() + " Antenna: " + tr.getAntenna() + " Read Count: " + tr.getReadCount() + "  RSSI: " + tr.getRssi());
			} else if(tr.getTag().epcString().equals("300833B2DDD9014000000000")){
				item = rf.getItem(tr.getTag().epcString());
				item.addData(tr);
//                System.out.println("Tag ID: " + tr.getTag().epcString() +" Time: " + tr.getTime() + " Frequency: " + tr.getFrequency() + " Antenna: " + tr.getAntenna() + " Read Count: " + tr.getReadCount() + "  RSSI: " + tr.getRssi());
			}
		}

	}

	static class TagReadExceptionReceiver implements ReadExceptionListener {

		String strDateFormat = "M/d/yyyy h:m:s a";
		SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

		public void tagReadException(com.thingmagic.Reader r, ReaderException re) {
			String format = sdf.format(Calendar.getInstance().getTime());
			System.out.println("Reader Exception: " + re.getMessage() + " Occured on :" + format);
			if (re.getMessage().equals("Connection Lost")) {
//                System.exit(1);
			}
		}
	}

	static int[] parseAntennaList(String[] args, int argPosition) {
		int[] antennaList = null;
		try {
			String argument = args[argPosition + 1];
			String[] antennas = argument.split(",");
			int i = 0;
			antennaList = new int[antennas.length];
			for (String ant : antennas) {
				antennaList[i] = Integer.parseInt(ant);
				i++;
			}
		} catch (IndexOutOfBoundsException ex) {
			System.out.println("Missing argument after " + args[argPosition]);
			usage();
		} catch (Exception ex) {
			System.out.println("Invalid argument at position " + (argPosition + 1) + ". " + ex.getMessage());
			usage();
		}
		return antennaList;
	}

}
