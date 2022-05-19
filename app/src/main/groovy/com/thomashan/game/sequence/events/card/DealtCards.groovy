package com.thomashan.game.sequence.events.card

import com.thomashan.game.sequence.domain.card.Card
import com.thomashan.game.sequence.domain.player.Player
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
class DealtCards implements DealCardsEvent {
    final Map<Player, List<Card>> dealtCards

    DealtCards(Map<Player, List<Card>> dealtCards) {
        this.dealtCards = dealtCards
    }

    List<Card> getAllCards() {
        return dealtCards.entrySet().stream()
            .map({ it.value })
            .flatMap({ it.stream() })
            .toList()
    }
}
