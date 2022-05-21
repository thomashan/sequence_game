package com.thomashan.game.sequence.command.card

import com.thomashan.game.sequence.domain.card.Card
import com.thomashan.game.sequence.domain.card.CardNumber
import com.thomashan.game.sequence.domain.card.Suite
import com.thomashan.game.sequence.domain.game.Game
import com.thomashan.game.sequence.domain.game.GameUtil
import com.thomashan.game.sequence.domain.player.Player
import org.junit.jupiter.api.Test

class DealSetCardsTest {
    @Test
    void testNew() {
        Game game = GameUtil.started()
        Player player = game.players()[0]
        new DealSetCards(game, [player: [Card.of(CardNumber.TEN, Suite.SPADE)]])
    }
}
