package software.galaniberico.restorer.configuration

import android.app.Application
import android.util.Log
import software.galaniberico.moduledroid.facade.Facade
import software.galaniberico.moduledroid.subcomponents.kernelconfigurator.PluginConfigurator
import software.galaniberico.restorer.data.RestoreData
import software.galaniberico.restorer.restorer.RestorerManager

class RestorerConfigurator : PluginConfigurator{
    override fun configure(app: Application) {
        Log.i(PLUGIN_LOG_TAG, "Starting plugin configuration")

        Facade.addOnSaveInstanceStateSubscription{ activity, _ ->
            RestorerManager.save(activity)
        }

        Facade.addOnStartSubscription{
            RestorerManager.restore(it)
        }


        Log.i(PLUGIN_LOG_TAG, "Plugin configured successfully")
    }
}