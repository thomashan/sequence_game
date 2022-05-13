package com.thomashan.game.sequence.events.card

import com.thomashan.game.sequence.domain.card.Card
import com.thomashan.game.sequence.domain.card.CardDeck
import com.thomashan.game.sequence.domain.player.Player
import groovy.transform.ImmutableOptions

@ImmutableOptions(knownImmutables = ["cardDeck"])
record DealtSetCards(CardDeck cardDeck, Map<Player, List<Card>> dealtCards) implements DealCardsEvent {
}
