package com.thomashan.game.sequence.domain.player

import com.thomashan.game.sequence.domain.card.Card
import com.thomashan.game.sequence.events.card.DrewCard

record Player(String name, int maxCards, List<Card> cards) {
    Player plus(DrewCard drewCard) {
        return new Player(name, maxCards, cards + drewCard.cardDeck().cards().head())
    }
}
