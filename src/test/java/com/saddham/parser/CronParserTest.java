package com.saddham.parser;

import com.saddham.model.ICron;
import com.saddham.model.definition.CronDefinitionBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CronParserTest {

    @Test
    void parseOnCronExpression() {
        String cronExpr = "1 0 15 5 1";

        CronParser cronParser = new CronParser(CronDefinitionBuilder.unixCron());
        ICron cron = cronParser.parse(cronExpr);

        assertEquals(cronExpr, cron.asString(), "Cron expression incorrectly parsed");
    }

    @Test
    void parseAndCronExpression() {
        String cronExpr = "1 0 1,15 5 1";

        CronParser cronParser = new CronParser(CronDefinitionBuilder.unixCron());
        ICron cron = cronParser.parse(cronExpr);

        assertEquals(cronExpr, cron.asString(), "Cron expression incorrectly parsed");
    }

    @Test
    void parseEveryCronExpression() {
        String cronExpr = "*/15 0 15 1 5";

        CronParser cronParser = new CronParser(CronDefinitionBuilder.unixCron());
        ICron cron = cronParser.parse(cronExpr);

        assertEquals(cronExpr, cron.asString(), "Cron expression incorrectly parsed");
    }

    @Test
    void parseBetweenCronExpression() {
        String cronExpr = "1-15 0 15 1 5";

        CronParser cronParser = new CronParser(CronDefinitionBuilder.unixCron());
        ICron cron = cronParser.parse(cronExpr);

        assertEquals(cronExpr, cron.asString(), "Cron expression incorrectly parsed");
    }

    @Test
    void parseAlwaysEveryCronExpression() {
        String cronExpr = "*/2 0 15 1 5";

        CronParser cronParser = new CronParser(CronDefinitionBuilder.unixCron());
        ICron cron = cronParser.parse(cronExpr);

        assertEquals(cronExpr, cron.asString(), "Cron expression incorrectly parsed");
    }

    @Test
    void parseBetweenEveryCronExpression() {
        String cronExpr = "0-30/2 23 31 12 0";

        CronParser cronParser = new CronParser(CronDefinitionBuilder.unixCron());
        ICron cron = cronParser.parse(cronExpr);

        assertEquals(cronExpr, cron.asString(), "Cron expression incorrectly parsed");
    }

    @Test
    void parseBetweenEveryAndCronExpression() {
        String cronExpr = "59 0-12/4,18-23/2 31 12 0";

        CronParser cronParser = new CronParser(CronDefinitionBuilder.unixCron());
        ICron cron = cronParser.parse(cronExpr);

        assertEquals(cronExpr, cron.asString(), "Cron expression incorrectly parsed");
    }

    @Test
    void parseCronExpressionWithEdgeValues() {
        String cronExpr = "0-59 0-23 1-31 1-12 0-6";

        CronParser cronParser = new CronParser(CronDefinitionBuilder.unixCron());
        ICron cron = cronParser.parse(cronExpr);

        assertEquals(cronExpr, cron.asString(), "Cron expression incorrectly parsed");
    }

    @Test
    void parseCronExpressionWithNegativeValue() {
        String cronExpr = "-1 0 1 1 0";

        CronParser cronParser = new CronParser(CronDefinitionBuilder.unixCron());
        assertThrows(IllegalArgumentException.class, () -> cronParser.parse(cronExpr));
    }

    @Test
    void parseCronExpressionWithMissingValue() {
        String cronExpr = "1 1 1 1";

        CronParser cronParser = new CronParser(CronDefinitionBuilder.unixCron());
        assertThrows(IllegalArgumentException.class, () -> cronParser.parse(cronExpr));
    }

    @Test
    void parseCronExpressionWithExtraValue() {
        String cronExpr = "1 1 1 1 1 1";

        CronParser cronParser = new CronParser(CronDefinitionBuilder.unixCron());
        assertThrows(IllegalArgumentException.class, () -> cronParser.parse(cronExpr));
    }
}