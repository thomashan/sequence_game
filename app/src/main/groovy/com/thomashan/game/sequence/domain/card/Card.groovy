package com.thomashan.game.sequence.domain.card

import static CardNumber.JACK
import static com.thomashan.game.sequence.domain.card.Suite.*

record Card(CardNumber number, Suite suite) {
    boolean wild() {
        (suite == CLUB || suite == DIAMOND) && number == JACK
    }

    boolean remove() {
        (suite == SPADE || suite == HEART) && number == JACK
    }

    static Card of(CardNumber number, Suite suite) {
        return new Card(number, suite)
    }
}
