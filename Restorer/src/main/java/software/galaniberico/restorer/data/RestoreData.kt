package software.galaniberico.restorer.data

import android.app.Activity
import software.galaniberico.moduledroid.facade.Facade
import software.galaniberico.restorer.exceptions.BlankIdFieldException

class RestoreData {
    private var data = mutableMapOf<String, Any?>()

    fun get(id: String) : Pair<Any?, Boolean> {
        if (id.isBlank()) throw BlankIdFieldException("The id field cannot be blank. Please revise the parameter value")
        if (data.containsKey(id)) {
            return Pair(data[id], true)
        }
        return Pair(null, false)
    }

    companion object {
        private var datas = mutableMapOf<String, RestoreData>()

        fun of(activity: Activity) : RestoreData? {
            return datas[Facade.getInternalId(activity)]
        }

        fun set(activity: Activity, data: RestoreData) {
            datas[Facade.getInternalId(activity)] = data
        }

        fun remove(activity: Activity) : RestoreData? {
            return datas.remove(Facade.getInternalId(activity))
        }

        internal var restoreIncome: MutableMap<String, Any?>? = null

        internal fun prepareIncome(){
            restoreIncome = mutableMapOf()
        }

        internal fun resolveIncome(restoreData: RestoreData) {
            val data = restoreIncome
            restoreIncome = null
            restoreData.data = data ?: mutableMapOf()
        }

    }
}

object RestoreDataActions {
    fun get(id: String): Pair<Any?, Boolean> {
        val data = RestoreData.of(Facade.getPreferredActivityOrFail()) ?: return Pair(null, false)
        return data.get(id)
    }
}