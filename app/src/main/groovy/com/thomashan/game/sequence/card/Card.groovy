package com.thomashan.game.sequence.card

record Card(CardNumber number, Suite suite) {
    static Card of(CardNumber number, Suite suite) {
        return new Card(number, suite)
    }
}
