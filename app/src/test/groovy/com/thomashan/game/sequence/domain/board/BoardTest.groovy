package com.thomashan.game.sequence.domain.board

import com.thomashan.game.sequence.domain.card.Card
import com.thomashan.game.sequence.domain.card.CardNumber
import com.thomashan.game.sequence.domain.card.Suite
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static com.thomashan.game.sequence.domain.card.Card.of
import static com.thomashan.game.sequence.domain.card.CardNumber.JACK

class BoardTest {
    private Board board

    @BeforeEach
    void setUp() {
        this.board = Board.standard()
    }

    @Test
    void testStandard_Size() {
        assert 10 == board.boardCells.size()
        board.boardCells.forEach({ assert 10 == it.size() })
    }

    @Test
    void testStandard_NoJacks() {
        List<Card> cards = Arrays.stream(Suite.values())
            .flatMap { Suite suite ->
                Arrays.stream(CardNumber.values() - JACK).map { CardNumber number -> of(number, suite) }
            }
            .toList()
        List<BoardCell> boardCells = board.boardCells.flatten()
        assert 4 == boardCells.findAll { it.wild() }.size()
        for (Card card : cards) {
            assert 2 == boardCells.findAll { it.card == Optional.of(card) }.size()
        }
    }
}
