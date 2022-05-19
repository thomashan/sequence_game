package com.thomashan.game.sequence.domain.game

import com.thomashan.game.sequence.GeneratedId
import com.thomashan.game.sequence.domain.card.CardDeck
import com.thomashan.game.sequence.domain.player.Player
import com.thomashan.game.sequence.events.card.DealCardsEvent
import com.thomashan.game.sequence.events.card.DealCardsFailed
import com.thomashan.game.sequence.events.card.DealtRandomCards
import com.thomashan.game.sequence.events.card.DealtSetCards
import groovy.transform.ImmutableOptions
import groovy.transform.TypeCheckingMode


@ImmutableOptions(knownImmutables = ["cardDeck"])
record Game(GameType gameType, List<Player> players, CardDeck cardDeck) implements GeneratedId {
    @groovy.transform.TypeChecked(TypeCheckingMode.SKIP)
    Game plus(DealCardsEvent event) {
        switch (event) {
            case DealtRandomCards -> {
                def (List<Player> newPlayers, CardDeck newCardDeck) = cardDeck.draw(players)
                new Game(gameType, newPlayers, newCardDeck)
            }
            case DealtSetCards -> {
                def (List<Player> newPlayers, CardDeck newCardDeck) = cardDeck.draw(players, event.dealtCards())
                new Game(gameType, newPlayers, newCardDeck)
            }
            case DealCardsFailed -> this
            default -> throw new RuntimeException("Unknown event ${event}")
        }
    }
}
