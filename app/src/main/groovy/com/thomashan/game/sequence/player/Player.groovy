package com.thomashan.game.sequence.player

import com.thomashan.game.sequence.card.Card
import com.thomashan.game.sequence.events.DrewCard

record Player(String name, int maxCards, List<Card> cards) {
    Player plus(DrewCard drewCard) {
        return new Player(name, maxCards, cards + drewCard.cardDeck().cards().head())
    }
}
