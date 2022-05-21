package com.thomashan.game.sequence.domain.board

import com.thomashan.game.sequence.domain.card.Card
import com.thomashan.game.sequence.domain.player.Player
import com.thomashan.game.sequence.events.card.DiscardedCard
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import static com.thomashan.game.sequence.domain.board.Position.of
import static com.thomashan.game.sequence.domain.card.CardNumber.*
import static com.thomashan.game.sequence.domain.card.Suite.*

// FIXME: make a position record, and make board cells map with position as key and card as value
@ToString
@EqualsAndHashCode
class Board {
    final Map<Position, BoardCell> boardCells

    Board(Map<Position, BoardCell> boardCells) {
        this.boardCells = boardCells
    }

    Board plus(DiscardedCard discardedCard) {
        return place(discardedCard.player(), discardedCard.card(), discardedCard.position())
    }

    Board place(Player player, Card card, Position position) {
        boardCells[position].card()
            .map({
                if (it == card || card.wild()) {
                    Map<Position, BoardCell> newBoardCells = new HashMap<>(boardCells)
                    BoardCell newBoardCell = newBoardCells[position].place(player)
                    newBoardCells[position] = newBoardCell
                    return new Board(newBoardCells)
                }
                if (card.remove() && boardCells[position].placed()) {
                    Map<Position, BoardCell> newBoardCells = new HashMap<>(boardCells)
                    BoardCell newBoardCell = newBoardCells[position].remove()
                    newBoardCells[position] = newBoardCell
                    return new Board(newBoardCells)
                }
                return this
            })
            .orElse(this)
    }

    static Board standard() {
        Map<Position, BoardCell> boardCells = new HashMap<>()
        boardCells.put(of(0, 0), BoardCell.of(Optional.empty()))
        boardCells.put(of(1, 0), BoardCell.of(Optional.of(Card.of(SIX, DIAMOND))))
        boardCells.put(of(2, 0), BoardCell.of(Optional.of(Card.of(SEVEN, DIAMOND))))
        boardCells.put(of(3, 0), BoardCell.of(Optional.of(Card.of(EIGHT, DIAMOND))))
        boardCells.put(of(4, 0), BoardCell.of(Optional.of(Card.of(NINE, DIAMOND))))
        boardCells.put(of(5, 0), BoardCell.of(Optional.of(Card.of(TEN, DIAMOND))))
        boardCells.put(of(6, 0), BoardCell.of(Optional.of(Card.of(QUEEN, DIAMOND))))
        boardCells.put(of(7, 0), BoardCell.of(Optional.of(Card.of(KING, DIAMOND))))
        boardCells.put(of(8, 0), BoardCell.of(Optional.of(Card.of(ACE, DIAMOND))))
        boardCells.put(of(9, 0), BoardCell.of(Optional.empty()))

        boardCells.put(of(0, 1), BoardCell.of(Optional.of(Card.of(FIVE, DIAMOND))))
        boardCells.put(of(1, 1), BoardCell.of(Optional.of(Card.of(THREE, HEART))))
        boardCells.put(of(2, 1), BoardCell.of(Optional.of(Card.of(TWO, HEART))))
        boardCells.put(of(3, 1), BoardCell.of(Optional.of(Card.of(TWO, SPADE))))
        boardCells.put(of(4, 1), BoardCell.of(Optional.of(Card.of(THREE, SPADE))))
        boardCells.put(of(5, 1), BoardCell.of(Optional.of(Card.of(FOUR, SPADE))))
        boardCells.put(of(6, 1), BoardCell.of(Optional.of(Card.of(FIVE, SPADE))))
        boardCells.put(of(7, 1), BoardCell.of(Optional.of(Card.of(SIX, SPADE))))
        boardCells.put(of(8, 1), BoardCell.of(Optional.of(Card.of(SEVEN, SPADE))))
        boardCells.put(of(9, 1), BoardCell.of(Optional.of(Card.of(ACE, CLUB))))

        boardCells.put(of(0, 2), BoardCell.of(Optional.of(Card.of(FOUR, DIAMOND))))
        boardCells.put(of(1, 2), BoardCell.of(Optional.of(Card.of(FOUR, HEART))))
        boardCells.put(of(2, 2), BoardCell.of(Optional.of(Card.of(KING, DIAMOND))))
        boardCells.put(of(3, 2), BoardCell.of(Optional.of(Card.of(ACE, DIAMOND))))
        boardCells.put(of(4, 2), BoardCell.of(Optional.of(Card.of(ACE, CLUB))))
        boardCells.put(of(5, 2), BoardCell.of(Optional.of(Card.of(KING, CLUB))))
        boardCells.put(of(6, 2), BoardCell.of(Optional.of(Card.of(QUEEN, CLUB))))
        boardCells.put(of(7, 2), BoardCell.of(Optional.of(Card.of(TEN, CLUB))))
        boardCells.put(of(8, 2), BoardCell.of(Optional.of(Card.of(EIGHT, SPADE))))
        boardCells.put(of(9, 2), BoardCell.of(Optional.of(Card.of(KING, CLUB))))

        boardCells.put(of(0, 3), BoardCell.of(Optional.of(Card.of(THREE, DIAMOND))))
        boardCells.put(of(1, 3), BoardCell.of(Optional.of(Card.of(FIVE, HEART))))
        boardCells.put(of(2, 3), BoardCell.of(Optional.of(Card.of(QUEEN, DIAMOND))))
        boardCells.put(of(3, 3), BoardCell.of(Optional.of(Card.of(QUEEN, HEART))))
        boardCells.put(of(4, 3), BoardCell.of(Optional.of(Card.of(TEN, HEART))))
        boardCells.put(of(5, 3), BoardCell.of(Optional.of(Card.of(NINE, HEART))))
        boardCells.put(of(6, 3), BoardCell.of(Optional.of(Card.of(EIGHT, HEART))))
        boardCells.put(of(7, 3), BoardCell.of(Optional.of(Card.of(NINE, CLUB))))
        boardCells.put(of(8, 3), BoardCell.of(Optional.of(Card.of(NINE, SPADE))))
        boardCells.put(of(9, 3), BoardCell.of(Optional.of(Card.of(QUEEN, CLUB))))

        boardCells.put(of(0, 4), BoardCell.of(Optional.of(Card.of(TWO, DIAMOND))))
        boardCells.put(of(1, 4), BoardCell.of(Optional.of(Card.of(SIX, HEART))))
        boardCells.put(of(2, 4), BoardCell.of(Optional.of(Card.of(TEN, DIAMOND))))
        boardCells.put(of(3, 4), BoardCell.of(Optional.of(Card.of(KING, HEART))))
        boardCells.put(of(4, 4), BoardCell.of(Optional.of(Card.of(THREE, HEART))))
        boardCells.put(of(5, 4), BoardCell.of(Optional.of(Card.of(TWO, HEART))))
        boardCells.put(of(6, 4), BoardCell.of(Optional.of(Card.of(SEVEN, HEART))))
        boardCells.put(of(7, 4), BoardCell.of(Optional.of(Card.of(EIGHT, CLUB))))
        boardCells.put(of(8, 4), BoardCell.of(Optional.of(Card.of(TEN, SPADE))))
        boardCells.put(of(9, 4), BoardCell.of(Optional.of(Card.of(TEN, CLUB))))

        boardCells.put(of(0, 5), BoardCell.of(Optional.of(Card.of(ACE, SPADE))))
        boardCells.put(of(1, 5), BoardCell.of(Optional.of(Card.of(SEVEN, HEART))))
        boardCells.put(of(2, 5), BoardCell.of(Optional.of(Card.of(NINE, DIAMOND))))
        boardCells.put(of(3, 5), BoardCell.of(Optional.of(Card.of(ACE, HEART))))
        boardCells.put(of(4, 5), BoardCell.of(Optional.of(Card.of(FOUR, HEART))))
        boardCells.put(of(5, 5), BoardCell.of(Optional.of(Card.of(FIVE, HEART))))
        boardCells.put(of(6, 5), BoardCell.of(Optional.of(Card.of(SIX, HEART))))
        boardCells.put(of(7, 5), BoardCell.of(Optional.of(Card.of(SEVEN, CLUB))))
        boardCells.put(of(8, 5), BoardCell.of(Optional.of(Card.of(QUEEN, SPADE))))
        boardCells.put(of(9, 5), BoardCell.of(Optional.of(Card.of(NINE, CLUB))))

        boardCells.put(of(0, 6), BoardCell.of(Optional.of(Card.of(KING, SPADE))))
        boardCells.put(of(1, 6), BoardCell.of(Optional.of(Card.of(EIGHT, HEART))))
        boardCells.put(of(2, 6), BoardCell.of(Optional.of(Card.of(EIGHT, DIAMOND))))
        boardCells.put(of(3, 6), BoardCell.of(Optional.of(Card.of(TWO, CLUB))))
        boardCells.put(of(4, 6), BoardCell.of(Optional.of(Card.of(THREE, CLUB))))
        boardCells.put(of(5, 6), BoardCell.of(Optional.of(Card.of(FOUR, CLUB))))
        boardCells.put(of(6, 6), BoardCell.of(Optional.of(Card.of(FIVE, CLUB))))
        boardCells.put(of(7, 6), BoardCell.of(Optional.of(Card.of(SIX, CLUB))))
        boardCells.put(of(8, 6), BoardCell.of(Optional.of(Card.of(KING, SPADE))))
        boardCells.put(of(9, 6), BoardCell.of(Optional.of(Card.of(EIGHT, CLUB))))

        boardCells.put(of(0, 7), BoardCell.of(Optional.of(Card.of(QUEEN, SPADE))))
        boardCells.put(of(1, 7), BoardCell.of(Optional.of(Card.of(NINE, HEART))))
        boardCells.put(of(2, 7), BoardCell.of(Optional.of(Card.of(SEVEN, DIAMOND))))
        boardCells.put(of(3, 7), BoardCell.of(Optional.of(Card.of(SIX, DIAMOND))))
        boardCells.put(of(4, 7), BoardCell.of(Optional.of(Card.of(FIVE, DIAMOND))))
        boardCells.put(of(5, 7), BoardCell.of(Optional.of(Card.of(FOUR, DIAMOND))))
        boardCells.put(of(6, 7), BoardCell.of(Optional.of(Card.of(THREE, DIAMOND))))
        boardCells.put(of(7, 7), BoardCell.of(Optional.of(Card.of(TWO, DIAMOND))))
        boardCells.put(of(8, 7), BoardCell.of(Optional.of(Card.of(ACE, SPADE))))
        boardCells.put(of(9, 7), BoardCell.of(Optional.of(Card.of(SEVEN, CLUB))))

        boardCells.put(of(0, 8), BoardCell.of(Optional.of(Card.of(TEN, SPADE))))
        boardCells.put(of(1, 8), BoardCell.of(Optional.of(Card.of(TEN, HEART))))
        boardCells.put(of(2, 8), BoardCell.of(Optional.of(Card.of(QUEEN, HEART))))
        boardCells.put(of(3, 8), BoardCell.of(Optional.of(Card.of(KING, HEART))))
        boardCells.put(of(4, 8), BoardCell.of(Optional.of(Card.of(ACE, HEART))))
        boardCells.put(of(5, 8), BoardCell.of(Optional.of(Card.of(TWO, CLUB))))
        boardCells.put(of(6, 8), BoardCell.of(Optional.of(Card.of(THREE, CLUB))))
        boardCells.put(of(7, 8), BoardCell.of(Optional.of(Card.of(FOUR, CLUB))))
        boardCells.put(of(8, 8), BoardCell.of(Optional.of(Card.of(FIVE, CLUB))))
        boardCells.put(of(9, 8), BoardCell.of(Optional.of(Card.of(SIX, CLUB))))

        boardCells.put(of(0, 9), BoardCell.of(Optional.empty()))
        boardCells.put(of(1, 9), BoardCell.of(Optional.of(Card.of(NINE, SPADE))))
        boardCells.put(of(2, 9), BoardCell.of(Optional.of(Card.of(EIGHT, SPADE))))
        boardCells.put(of(3, 9), BoardCell.of(Optional.of(Card.of(SEVEN, SPADE))))
        boardCells.put(of(4, 9), BoardCell.of(Optional.of(Card.of(SIX, SPADE))))
        boardCells.put(of(5, 9), BoardCell.of(Optional.of(Card.of(FIVE, SPADE))))
        boardCells.put(of(6, 9), BoardCell.of(Optional.of(Card.of(FOUR, SPADE))))
        boardCells.put(of(7, 9), BoardCell.of(Optional.of(Card.of(THREE, SPADE))))
        boardCells.put(of(8, 9), BoardCell.of(Optional.of(Card.of(TWO, SPADE))))
        boardCells.put(of(9, 9), BoardCell.of(Optional.empty()))
        return new Board(boardCells)
    }
}
