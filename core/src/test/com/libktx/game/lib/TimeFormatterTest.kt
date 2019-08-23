package com.libktx.game.lib


import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import java.util.*


internal class TimeFormatterTest : FunSpec({

    test("A time stamp should be formatted like") {
        TimeFormatter.getFormattedTimeAsString((42 * 60 + 30) * 1000) shouldBe "42:30"
        TimeFormatter.getFormattedTimeAsString((8 * 60 + 8) * 1000) shouldBe "08:08"
        TimeFormatter.getFormattedTimeAsString(0) shouldBe "00:00"
    }

    test("A date should be formatted like") {
        TimeFormatter.getFormattedDateAsString(Date(1565960438137)) shouldBe "2019.08.16.15:00.137"
    }

})