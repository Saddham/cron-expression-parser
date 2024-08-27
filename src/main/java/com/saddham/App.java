package com.saddham;

import com.saddham.descriptor.CronDescriptor;
import com.saddham.model.ICron;
import com.saddham.model.definition.CronDefinitionBuilder;
import com.saddham.parser.CronParser;

public class App {
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Incorrect number of arguments. Required number of argument: 2");
        }

        String cronExpression = args[0];
        String command = args[1];

        if ("/usr/bin/find".equals(command)) {
            CronParser cronParser = new CronParser(CronDefinitionBuilder.unixCron());
            ICron cron = cronParser.parse(cronExpression); // "*/15 0 1,15 * 1-5"

            CronDescriptor cronDescriptor = new CronDescriptor();
            String description = cronDescriptor.describe(cron);

            System.out.println();
            System.out.println(description);
            System.out.println(String.format("%-14s %s", "command", command));
        } else {
            throw new IllegalArgumentException(String.format("Command %s is not supported.", command));
        }
    }
}
