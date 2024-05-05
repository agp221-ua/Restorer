package software.galaniberico.restorer.restorer

import android.app.Activity
import software.galaniberico.moduledroid.facade.Facade
import software.galaniberico.restorer.data.RestoreData
import software.galaniberico.restorer.exceptions.DataTypeMismatchException
import software.galaniberico.restorer.exceptions.UnexpectedFunctionCallException
import software.galaniberico.restorer.tags.OnRestore
import software.galaniberico.restorer.tags.OnSave
import software.galaniberico.restorer.tags.Restore

object RestorerManager {
    fun save(activity: Activity) {
        val restoreData = RestoreData()
        RestoreData.prepareIncome()
        saveFields(activity)
        executeSaveMethods(activity)
        RestoreData.resolveIncome(restoreData)
        RestoreData.set(activity, restoreData)
    }

    fun restore(activity: Activity) {
        val restoreData = RestoreData.of(activity) ?: return
        restoreFields(activity, restoreData)
        executeRestoreMethods(activity)
        RestoreData.remove(activity)
    }

    private fun executeRestoreMethods(activity: Activity) {
        val clazz = activity.javaClass
        for (m in clazz.declaredMethods) {
            if (m.isAnnotationPresent(OnRestore::class.java)) {
                val a = m.isAccessible
                m.isAccessible = true
                m.invoke(activity)
                m.isAccessible = a
            }
        }
    }

    private fun restoreFields(activity: Activity, restoreData: RestoreData) {
        val clazz = activity.javaClass
        for (f in clazz.declaredFields) {
            if (f.isAnnotationPresent(Restore::class.java)) {
                try {
                    val (value, found) = restoreData.get(f.name)
                    if (!found) continue
                    val a = f.isAccessible
                    f.isAccessible = true
                    f.set(activity, value)
                    f.isAccessible = a
                } catch (e: IllegalArgumentException) {
                    throw DataTypeMismatchException("The retrieved data for id \"${f.name}\" is not of the expected type.")

                } catch (e: Exception) {
                    continue
                }
            }
        }
    }

    private fun saveFields(activity: Activity) {
        val clazz = activity.javaClass
        for (f in clazz.declaredFields) {
            if (f.isAnnotationPresent(Restore::class.java)) {
                val a = f.isAccessible
                f.isAccessible = true
                val value = f.get(activity)
                f.isAccessible = a
                RestoreData.restoreIncome?.set(f.name, value)
                    ?: throw UnexpectedFunctionCallException("You cannot add data outside of a OnSave function. This method should only be called inside a method annotated with the @OnSave tag.")
            }
        }
    }

    private fun executeSaveMethods(activity: Activity) {
        val clazz = activity.javaClass
        for (m in clazz.declaredMethods) {
            if (m.isAnnotationPresent(OnSave::class.java)) {
                val a = m.isAccessible
                m.isAccessible = true
                m.invoke(activity)
                m.isAccessible = a
            }
        }
    }

}