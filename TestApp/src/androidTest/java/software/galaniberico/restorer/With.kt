package software.galaniberico.restorer

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import software.galaniberico.restorer.exceptions.UnexpectedFunctionCallException
import software.galaniberico.restorer.facade.Restorer

@RunWith(AndroidJUnit4::class)
class With {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun withOutBounds() {
        Assert.assertThrows(UnexpectedFunctionCallException::class.java) {
            activityRule.scenario.onActivity {
                Restorer.with("id", "failing")
            }
        }
    }
}