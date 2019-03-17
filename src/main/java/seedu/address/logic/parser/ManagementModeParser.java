package seedu.address.logic.parser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.management.AddLessonCommand;
import seedu.address.logic.commands.management.ExitCommand;
import seedu.address.logic.commands.management.HelpCommand;
import seedu.address.logic.commands.management.HistoryCommand;
import seedu.address.logic.commands.quiz.QuizStartCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Lesson;

/**
 * Parses user input.
 */
public class ManagementModeParser implements Parser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        //        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        //        case FindCommand.COMMAND_WORD:
        //            return new FindCommandParser().parse(arguments);
        // TODO use parser here
        case QuizStartCommand.COMMAND_WORD:
            return new QuizStartCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddLessonCommand.COMMAND_WORD:
            Lesson lesson = new Lesson("Test Lesson", List.of("Question", "Answer"), List.of("Hint"));
            return new AddLessonCommand(lesson);

        //        case UndoCommand.COMMAND_WORD:
        //            return new UndoCommand();
        //
        //        case RedoCommand.COMMAND_WORD:
        //            return new RedoCommand();

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}