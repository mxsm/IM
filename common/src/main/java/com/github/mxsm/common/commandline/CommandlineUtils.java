package com.github.mxsm.common.commandline;


import java.util.Properties;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/6/27
 * @Since 1.0.0
 */
public abstract class CommandlineUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandlineUtils.class);

    public static Options buildCommandlineOptions(final Options options) {
        Option opt = new Option("h", "help", false, "Print help");
        opt.setRequired(false);
        options.addOption(opt);

        return options;
    }

    public static Options buildCommandlineOptions() {
        return buildCommandlineOptions(new Options());
    }


    public static CommandLine parseCmdLine(final String appName, String[] args, Options options,
        CommandLineParser parser) {

        CommandLine commandLine = null;
        try {
            commandLine = parser.parse(options, args);
            if (commandLine.hasOption('h')) {
                printCommandLineHelp(appName, options);
                System.exit(0);
            }
        } catch (ParseException e) {
            LOGGER.error("CommandLine Parse Error",e);
            printCommandLineHelp(appName, options);
            System.exit(1);
        }
        return commandLine;
    }

    public static void printCommandLineHelp(final String appName, final Options options) {
        HelpFormatter hf = new HelpFormatter();
        hf.setWidth(110);
        hf.printHelp(appName, options, true);
        System.exit(0);
    }

    public static Properties commandLine2Properties(final CommandLine commandLine) {
        Properties properties = new Properties();
        Option[] opts = commandLine.getOptions();

        if (opts != null) {
            for (Option opt : opts) {
                String name = opt.getLongOpt();
                String value = commandLine.getOptionValue(name);
                if (value != null) {
                    properties.setProperty(name, value.trim());
                }
            }
        }
        return properties;
    }

    public static int getOptionValue2Int(final CommandLine cmdLine, final String opt) {
        return Integer.parseInt(cmdLine.getOptionValue(opt).trim());
    }

    public static long getOptionValue2Long(final CommandLine cmdLine, final String opt) {
        return Long.parseLong(cmdLine.getOptionValue(opt).trim());
    }

    public static String getOptionValue(final CommandLine cmdLine, final String opt) {
        return cmdLine.getOptionValue(opt) == null ? null : cmdLine.getOptionValue(opt).trim();
    }
}
