package software.galaniberico.restorer

import android.content.pm.ActivityInfo
import android.widget.Button
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import software.galaniberico.moduledroid.facade.Facade
import software.galaniberico.restorer.exceptions.UnexpectedFunctionCallException
import software.galaniberico.restorer.facade.Restorer

@RunWith(AndroidJUnit4::class)
class Restoration {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        activityRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    @Test
    fun fieldsRestored() {
        activityRule.scenario.onActivity {
            it.string = "ok"
            it.int = 45
            it.notRestored = "fail"
            it.execution = "fieldsRestored"
            Facade.addOnStartSubscription {itt ->
                if ((itt as MainActivity).execution != "fieldsRestored")
                    return@addOnStartSubscription
                Assert.assertEquals("ok", (itt as MainActivity).string)
                Assert.assertEquals(45, (itt as MainActivity).int)
                Assert.assertEquals("default", (itt as MainActivity).notRestored)
            }
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }

    @Test
    fun methodsExecutted() {
        activityRule.scenario.onActivity {
            it.string = "ok"
            it.int = 45
            it.notRestored = "fail"
            it.findViewById<EditText>(R.id.hello).setText("ok")
            it.findViewById<Button>(R.id.button).performClick()
        }

        onView(withId(R.id.textView)).check(matches(withText("ok")))

        activityRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(withId(R.id.textView)).check(matches(withText("ok")))
    }


}