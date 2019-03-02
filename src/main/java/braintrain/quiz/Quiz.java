package braintrain.quiz;

import static braintrain.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a quiz that stores a list of QuizCard
 */
public class Quiz {

    public static final String MESSAGE_CONSTRAINTS = "Mode must only be learn/review/preview";

    private List<QuizCard> currentSession;
    private List<QuizCard> generatedSession;
    private Mode mode; // learn/review/preview
    private QuizCard currentQuizCard;
    private int currentCardIndex;
    private int generatedCardSize;
    private boolean end;

    /**
     * Different types of mode supported in Quiz.
     * Learn: sees both the question and answer first then get tested.
     * Review: only get tested.
     * Preview: sees both question and answer but not tested.
     */
    public enum Mode {
        LEARN,
        REVIEW,
        PREVIEW
    }

    /**
     * Build constructor from session
     * @param session contains a list of question, answer and list of optional
     */
    public Quiz(List<QuizCard> session, Mode mode) {
        requireNonNull(session);
        checkArgument(mode != null, MESSAGE_CONSTRAINTS);

        this.currentSession = session;
        this.mode = mode;
        this.currentCardIndex = -1;
        this.generatedCardSize = -1;
        this.end = false;

        generate();
    }

    /**
     * Generates a list of cards based on the chosen cards given by session.
     */
    public List<QuizCard> generate() {
        QuizCard currentCard;
        generatedSession = new ArrayList<>();

        if (mode != Mode.PREVIEW) {
            for (int i = 0; i < currentSession.size(); i++) {
                currentCard = currentSession.get(i);
                generatedSession.add(new QuizCard(i, currentCard.getQuestion(), currentCard.getAnswer()));
            }

            for (int i = 0; i < currentSession.size(); i++) {
                currentCard = currentSession.get(i);
                generatedSession.add(new QuizCard(i, currentCard.getAnswer(), currentCard.getQuestion()));
            }

        } else {
            generatedSession = currentSession;
        }

        generatedCardSize = generatedSession.size();
        return generatedSession;
    }

    /**
     * Returns true if there is card left in quiz.
     */
    public boolean isNextCard() {
        return currentCardIndex < (generatedCardSize - 1);
    }

    /**
     * Returns the next card in line.
     */
    public QuizCard getNextCard() {
        currentCardIndex++;

        if (currentCardIndex < generatedCardSize) {
            currentQuizCard = generatedSession.get(currentCardIndex);
            return currentQuizCard;
        }

        throw new IndexOutOfBoundsException("No cards left.");
    }

    /**
     * Update the totalAttempts and streak of a specified card in the current session.
     * @param index of the card
     * @param answer user input
     */
    public void updateTotalAttemptsandStreak(int index, String answer) {
        QuizCard sessionCard = currentSession.get(index);
        sessionCard.updateTotalAttemptsandStreak(currentQuizCard.isCorrect(answer));
    }

    /**
     * Returns the current session.
     */
    public List<QuizCard> getCurrentSession() {
        return currentSession;
    }

    /**
     * Format data needed by Session
     * @return a list of index of card, total attempts and streak in this session.
     */
    public List<List<Integer>> end() {
        this.end = true;

        List<List<Integer>> session = new ArrayList<>();
        QuizCard card;
        for (int i = 0; i < currentSession.size(); i++) {
            card = currentSession.get(i);
            session.add(Arrays.asList(i, card.getTotalAttempts(), card.getStreak()));
        }

        return session;
    }

    public boolean isEnd() {
        return end;
    }
}
