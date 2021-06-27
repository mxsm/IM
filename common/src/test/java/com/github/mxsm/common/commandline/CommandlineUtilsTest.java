package com.github.mxsm.common.commandline;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Properties;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author mxsm
 * @Date 2021/6/27
 * @Since
 */
class CommandlineUtilsTest {

    private Options options;

    @BeforeEach
    void setUp() {
        options = CommandlineUtils.buildCommandlineOptions();
    }

    @Test
    void buildCommandlineOptions() {
        Options options = CommandlineUtils.buildCommandlineOptions();
        Assertions.assertNotNull(options);
    }

    @Test
    void testBuildCommandlineOptions() {
        Options options = CommandlineUtils.buildCommandlineOptions(this.options);
        Assertions.assertNotNull(options);
    }

    @Test
    void parseCmdLine() {

        String[] args = new String[]{"-p 8080"};
        options.addOption(new Option("p", "port",true,"list port"));
        CommandLine commandLine = CommandlineUtils.parseCmdLine("BB", args, options, new DefaultParser());
        Assertions.assertNotNull(commandLine);
        boolean hasOption = commandLine.hasOption("p");
        Assertions.assertTrue(hasOption);
    }

    @Test
    void printCommandLineHelp() {
        CommandlineUtils.printCommandLineHelp("AA",options);
    }

    @Test
    void commandLine2Properties() {
        String[] args = new String[]{"-p 8080"};
        options.addOption(new Option("p", "port",true,"list port"));
        CommandLine commandLine = CommandlineUtils.parseCmdLine("BB", args, options, new DefaultParser());
        Properties properties = CommandlineUtils.commandLine2Properties(commandLine);
        Assertions.assertNotNull(properties);
        String property = properties.getProperty("port");
        Assertions.assertEquals("8080", property);
    }

    @Test
    void getOptionValue2Int() {
        String[] args = new String[]{"-p 8080"};
        options.addOption(new Option("p", "port",true,"list port"));
        CommandLine commandLine = CommandlineUtils.parseCmdLine("BB", args, options, new DefaultParser());
        Assertions.assertEquals(8080, CommandlineUtils.getOptionValue2Int(commandLine,"p"));
    }

    @Test
    void getOptionValue2Long() {
        String[] args = new String[]{"-p 8080"};
        options.addOption(new Option("p", "port",true,"list port"));
        CommandLine commandLine = CommandlineUtils.parseCmdLine("BB", args, options, new DefaultParser());
        Assertions.assertEquals(8080L, CommandlineUtils.getOptionValue2Long(commandLine,"p"));
    }

    @Test
    void getOptionValue() {
        String[] args = new String[]{"-p 8080"};
        options.addOption(new Option("p", "port",true,"list port"));
        CommandLine commandLine = CommandlineUtils.parseCmdLine("BB", args, options, new DefaultParser());
        Assertions.assertEquals("8080", CommandlineUtils.getOptionValue(commandLine,"p"));
    }
}