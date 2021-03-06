package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndices.INDEX_FIRST;

import org.junit.Test;

import seedu.address.logic.commands.management.EditLessonCommand;

public class OpenLessonParserTest {

    private OpenLessonParser parser = new OpenLessonParser();

    @Test
    public void parse_validArgs_returnsOpenCommand() {
        assertParseSuccess(parser, "" + INDEX_FIRST.getOneBased(),
                new EditLessonCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditLessonCommand.MESSAGE_USAGE));
    }
}
