package com.thomashan.game.sequence

import java.time.Instant

trait Timestamped {
    final Instant timestamp = Instant.now()
}
