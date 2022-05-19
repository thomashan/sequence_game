package com.thomashan.game.sequence

trait GeneratedId extends Id {
    final String id = UUID.randomUUID().toString()
}
