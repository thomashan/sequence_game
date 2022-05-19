package com.thomashan.game.sequence.command.card

import com.thomashan.game.sequence.domain.card.Card
import com.thomashan.game.sequence.domain.game.Game
import com.thomashan.game.sequence.domain.player.Player
import com.thomashan.game.sequence.events.card.DrawCardEvent
import com.thomashan.game.sequence.events.card.DrawCardFailed
import com.thomashan.game.sequence.events.card.DrewCard
import groovy.transform.ImmutableOptions

@ImmutableOptions(knownImmutables = ["game", "player", "card"])
record DrawSetCard(Game game, Player player, Card card) implements DrawCard {
    @Override
    DrawCardEvent execute() {
        Optional<DrawCardFailed> failureEvent = failureEvent
        if (!failureEvent.empty) {
            return failureEvent.get()
        }
        if (!game.cardDeck().cards().contains(card)) {
            return new DrawCardFailed("The deck doesn't have card[${card}]")
        }
        return new DrewCard(player, card)
    }

    @Override
    Game getGame() {
        return game
    }

    @Override
    Player getPlayer() {
        return player
    }
}
