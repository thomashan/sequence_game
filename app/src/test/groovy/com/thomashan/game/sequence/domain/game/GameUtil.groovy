package com.thomashan.game.sequence.domain.game

import com.thomashan.game.sequence.domain.board.Board
import com.thomashan.game.sequence.domain.board.BoardCell
import com.thomashan.game.sequence.domain.board.Position
import com.thomashan.game.sequence.domain.card.CardDeck
import com.thomashan.game.sequence.domain.player.Colour
import com.thomashan.game.sequence.domain.player.Player
import com.thomashan.game.sequence.domain.player.Players

class GameUtil {
    private GameUtil() {
        throw new AssertionError("not instantiable")
    }

    static Game started() {
        Player player = new Player("name", Colour.RED, 7, true, [])
        return new Game(GameType.STANDARD, GameState.WAITING_FOR_CARDS_TO_BE_DEALT, 0, new Players([player]), CardDeck.standardDeck(), Board.standard())
    }

    static Game waitingToDiscard() {
        return waitingToDiscard([])
    }

    static Game waitingToDiscard(List<Tuple2<Position, BoardCell>> boardCells) {
        Player player = new Player("name", Colour.RED, 7, true, [])
        Map<Position, BoardCell> newBoardCells = new HashMap<>(Board.standard().boardCells)
        boardCells.forEach { newBoardCells[it.v1] = it.v2 }
        Board newBoard = new Board(newBoardCells)
        return new Game(GameType.STANDARD, GameState.WAITING_FOR_PLAYER_TO_DISCARD, 0, new Players([player]), CardDeck.standardDeck(), newBoard)
    }
}
