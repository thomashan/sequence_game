package com.thomashan.game.sequence.command.card


import com.thomashan.game.sequence.domain.board.Position
import com.thomashan.game.sequence.domain.game.Game
import com.thomashan.game.sequence.domain.game.GameUtil
import com.thomashan.game.sequence.domain.player.Colour
import com.thomashan.game.sequence.domain.player.Player
import com.thomashan.game.sequence.events.card.DiscardCardEvent
import com.thomashan.game.sequence.events.card.DiscardCardFailed
import com.thomashan.game.sequence.events.card.DiscardedCard
import org.junit.jupiter.api.Test

import static com.thomashan.game.sequence.domain.card.Card.of
import static com.thomashan.game.sequence.domain.card.CardNumber.JACK
import static com.thomashan.game.sequence.domain.card.Suite.CLUB
import static com.thomashan.game.sequence.domain.card.Suite.SPADE

class DiscardCardTest {
    @Test
    void testExecute_CannotPlaceInWildBoardCell() {
        Game game = GameUtil.waitingToDiscard()
        DiscardCardEvent discardCardEvent = new DiscardCard(game, game.players().value()[0], of(JACK, CLUB), Position.of(0, 0)).execute()
        assert DiscardCardFailed == discardCardEvent.class
    }

    @Test
    void testExecute_CanPlacedAnywhere() {
        Game game = GameUtil.waitingToDiscard()
        DiscardCardEvent discardCardEvent = new DiscardCard(game, game.players().value()[0], of(JACK, CLUB), Position.of(1, 0)).execute()
        assert DiscardedCard == discardCardEvent.class
    }

    @Test
    void testExecute_CannotRemoveIfNoChipIsPlaced() {
        Game game = GameUtil.waitingToDiscard()
        DiscardCardEvent discardCardEvent = new DiscardCard(game, game.players().value()[0], of(JACK, SPADE), Position.of(1, 0)).execute()
        assert DiscardCardFailed == discardCardEvent.class
    }

    @Test
    void testExecute_CanRemoveIfOppositionChipIsPlaced() {
        Game game = GameUtil.waitingToDiscard()
        Player otherPlayer = new Player("name", Colour.GREEN, 7, true, [])
        Position position = Position.of(1, 0)
        game = GameUtil.waitingToDiscard([new Tuple2(position, game.board().boardCells[position].place(otherPlayer))])
        DiscardCardEvent discardCardEvent = new DiscardCard(game, game.players().value()[0], of(JACK, SPADE), Position.of(1, 0)).execute()
        assert DiscardedCard == discardCardEvent.class
    }

    @Test
    void testExecute_CannotRemoveIfSelfChipIsPlaced() {
        Game game = GameUtil.waitingToDiscard()
        Position position = Position.of(1, 0)
        game = GameUtil.waitingToDiscard([new Tuple2(position, game.board().boardCells[position].place(game.players().value()[0]))])
        DiscardCardEvent discardCardEvent = new DiscardCard(game, game.players().value()[0], of(JACK, SPADE), Position.of(1, 0)).execute()
        assert DiscardCardFailed == discardCardEvent.class
    }
}
