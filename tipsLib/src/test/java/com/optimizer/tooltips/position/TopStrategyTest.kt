package com.optimizer.tooltips.position

import com.optimizer.tooltips.entity.Bounds
import com.optimizer.tooltips.entity.Point
import com.optimizer.tooltips.utils.MARGIN_BOTTOM_INDEX
import com.optimizer.tooltips.utils.MARGIN_LEFT_INDEX
import com.optimizer.tooltips.utils.MARGIN_RIGHT_INDEX
import com.optimizer.tooltips.utils.MARGIN_TOP_INDEX
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class TopStrategyTest {

    private lateinit var positionCalculator: PositionCalculator

    private val tipMargins by lazy {
        intArrayOf(20, 20, 20, 20)
    }

    private val tipViewBounds by lazy {
        Bounds(0f, 0f, 300f, 100f)
    }

    private val anchorViewBounds by lazy {
        Bounds(0f, 0f, 100f, 150f)
    }

    @Test
    fun `tip aligned left`() {
        val anchorWindowPosition = Point(0f, 500f)
        positionCalculator = TopStrategy(TipHorizontalGravity.LEFT)

        val correctX = anchorWindowPosition.x + tipMargins[MARGIN_LEFT_INDEX]
        val correctY = anchorWindowPosition.y - tipViewBounds.getHeight() - tipMargins[MARGIN_BOTTOM_INDEX]
        val position = positionCalculator.computePosition(tipViewBounds, anchorViewBounds, anchorWindowPosition, tipMargins)

        assertThat("Position X should equal to the left margin", position.x == correctX)
        assertThat("Tip view should be above the anchor view. Don`t forget to add wished margin.", position.y == correctY)
    }

    @Test
    fun `tip aligned left with extra margin`() {
        val anchorWindowPosition = Point(40f, 20f)
        positionCalculator = TopStrategy(TipHorizontalGravity.LEFT)

        val correctX = anchorWindowPosition.x + tipMargins[MARGIN_LEFT_INDEX]
        val correctY = anchorWindowPosition.y - tipViewBounds.getHeight() - tipMargins[MARGIN_BOTTOM_INDEX]
        val position = positionCalculator.computePosition(tipViewBounds, anchorViewBounds, anchorWindowPosition, tipMargins)

        assertThat("Position X should equal to the sum of left margin and anchor x position", position.x == correctX)
        assertThat("Position Y should equal to the sum of top margin, anchor view height and anchor y position", position.y == correctY)
    }

    @Test
    fun `tip aligned right`() {
        val anchorWindowPosition = Point(350f, 20f)
        positionCalculator = TopStrategy(TipHorizontalGravity.RIGHT)

        val correctPositionX = anchorWindowPosition.x + anchorViewBounds.getWidth() - tipViewBounds.getWidth() - tipMargins[MARGIN_RIGHT_INDEX]
        val correctY = anchorWindowPosition.y - tipViewBounds.getHeight() - tipMargins[MARGIN_BOTTOM_INDEX]
        val position = positionCalculator.computePosition(tipViewBounds, anchorViewBounds, anchorWindowPosition, tipMargins)

        assertThat("Right edge of the tip view should be aligned to the right edge of the anchored view, " +
                "and don`t forget to correct x coordinate with wished margin", position.x == correctPositionX)
        assertThat("Tip view should be above the anchored view", position.y == correctY)
    }

    @Test
    fun `tip aligned center`() {
        val anchorWindowPosition = Point(150f, 20f)
        positionCalculator = TopStrategy(TipHorizontalGravity.CENTER)

        val correctX = anchorWindowPosition.x + anchorViewBounds.getWidth() / 2 - tipViewBounds.getWidth() / 2
        val correctY = anchorWindowPosition.y - tipViewBounds.getHeight() - tipMargins[MARGIN_BOTTOM_INDEX]
        val position = positionCalculator.computePosition(tipViewBounds, anchorViewBounds, anchorWindowPosition, tipMargins)

        assertThat("Center of the tip view should exactly the center of the anchor view. Notice, that " +
                "margins should be ignored in this case", position.x == correctX)
        assertThat("Tip view should be above the anchored view", position.y == correctY)
    }
}