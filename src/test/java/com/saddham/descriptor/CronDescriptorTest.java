package com.saddham.descriptor;

import com.saddham.model.ICron;
import com.saddham.model.definition.CronDefinitionBuilder;
import com.saddham.parser.CronParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CronDescriptorTest {

    @Test
    void describeOnCronExpression() {
        ICron cron = getCron("1 0 15 5 1");
        String expectedCronDescription = """
                minute         1
                hour           0
                day of month   15
                month          5
                day of week    1""";

        verifyCronDescription(cron, expectedCronDescription);
    }

    @Test
    void describeAndCronExpression() {
        ICron cron = getCron("1 0 1,15 5 1");
        String expectedCronDescription = """
                minute         1
                hour           0
                day of month   1 15
                month          5
                day of week    1""";

        verifyCronDescription(cron, expectedCronDescription);
    }

    @Test
    void describeEveryCronExpression() {
        ICron cron = getCron("*/15 0 15 1 5");
        String expectedCronDescription = """
                minute         0 15 30 45
                hour           0
                day of month   15
                month          1
                day of week    5""";

        verifyCronDescription(cron, expectedCronDescription);
    }

    @Test
    void describeBetweenCronExpression() {
        String cronExpr = "1-15 0 15 1 5";
        String expectedCronDescription = """
                minute         1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
                hour           0
                day of month   15
                month          1
                day of week    5""";

        verifyCronDescription(getCron(cronExpr), expectedCronDescription);
    }

    @Test
    void describeAlwaysEveryCronExpression() {
        String cronExpr = "1 */8 15 1 5";
        String expectedCronDescription = """
                minute         1
                hour           0 8 16
                day of month   15
                month          1
                day of week    5""";

        verifyCronDescription(getCron(cronExpr), expectedCronDescription);
    }

    @Test
    void describeBetweenEveryCronExpression() {
        String cronExpr = "0-10/2 23 31 12 0";
        String expectedCronDescription = """
                minute         0 2 4 6 8 10
                hour           23
                day of month   31
                month          12
                day of week    0""";

        verifyCronDescription(getCron(cronExpr), expectedCronDescription);
    }

    @Test
    void describeBetweenEveryAndCronExpression() {
        String cronExpr = "59 0-12/4,18-23/2 31 12 0";
        String expectedCronDescription = """
                minute         59
                hour           0 4 8 12 18 20 22
                day of month   31
                month          12
                day of week    0""";

        verifyCronDescription(getCron(cronExpr), expectedCronDescription);
    }

    @Test
    void describeCronExpressionWithEdgeValues() {
        String cronExpr = "0-59 0-23 1-31 1-12 0-6";
        String expectedCronDescription = """
                minute         0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59
                hour           0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23
                day of month   1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31
                month          1 2 3 4 5 6 7 8 9 10 11 12
                day of week    0 1 2 3 4 5 6""";

        verifyCronDescription(getCron(cronExpr), expectedCronDescription);
    }


    private void verifyCronDescription(ICron cronExpr, String expectedCronDescription) {
        CronDescriptor cronDescriptor = new CronDescriptor();
        String actualCronDescription = cronDescriptor.describe(cronExpr);

        assertEquals(expectedCronDescription, actualCronDescription, "Incorrect cron description");
    }

    private ICron getCron(String cronExpr) {
        CronParser cronParser = new CronParser(CronDefinitionBuilder.unixCron());

        return cronParser.parse(cronExpr);
    }
}