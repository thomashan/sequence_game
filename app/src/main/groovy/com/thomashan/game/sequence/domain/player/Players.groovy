package com.thomashan.game.sequence.domain.player

import com.thomashan.game.sequence.events.card.DealtCards
import com.thomashan.game.sequence.events.card.DiscardedCard
import com.thomashan.game.sequence.events.card.DrewCard

import java.util.stream.Collectors

record Players(List<Player> value) {
    Players plus(DrewCard drewCard) {
        new Players(value.stream()
            .map({
                if (it == drewCard.player()) {
                    return it + drewCard.card()
                }
                return it
            })
            .toList()
        )
    }

    Players plus(DiscardedCard discardedCard) {
        return new Players(value.stream()
            .map({
                if (it == discardedCard.player()) {
                    return it - discardedCard.card()
                }
                return it
            })
            .toList()
        )
    }

    Players plus(DealtCards dealtCards) {
        return new Players(value.stream()
            .map({
                if (dealtCards.dealtCards.containsKey(it)) {
                    return new Player(it.name(), it.colour(), it.maxCards(), it.allowAnyDiscard(), dealtCards.dealtCards[it])
                }
                return new Player(it.name(), it.colour(), it.maxCards(), true, it.cards())
            })
            .toList()
        )
    }

    Player getAt(int index) {
        return value[index]
    }

    int size() {
        return value.size()
    }

    Set<Colour> getUniqueColours() {
        return value.stream()
            .map({ it.colour() })
            .distinct()
            .collect(Collectors.toSet())
    }

    Players initialise() {
        int maxCards = maxCards
        List<Player> newValue = value.stream()
            .map({ player -> new Player(player.name(), player.colour(), maxCards, player.allowAnyDiscard(), []) })
            .toList()
        return new Players(newValue)
    }

    private int getMaxCards() {
        return switch (value.size()) {
            case 2 -> 7
            case 3 -> 6
            default -> throw new RuntimeException("Only 2 or 3 players allowed")
        }
    }
}
