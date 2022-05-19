package com.thomashan.game.sequence.command.card

import com.thomashan.game.sequence.domain.card.Card
import com.thomashan.game.sequence.domain.card.CardDeck
import com.thomashan.game.sequence.domain.card.CardNumber
import com.thomashan.game.sequence.domain.card.Suite
import com.thomashan.game.sequence.domain.player.Player
import org.junit.jupiter.api.Test

class DealSetCardsTest {
    @Test
    void testNew() {
        new DealSetCards(CardDeck.standardDeck(), [new Player("name", 7, []): [Card.of(CardNumber.TEN, Suite.SPADE)]])
    }
}
