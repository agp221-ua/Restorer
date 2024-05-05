package software.galaniberico.restorer.facade

import software.galaniberico.restorer.data.RestoreData
import software.galaniberico.restorer.data.RestoreDataActions
import software.galaniberico.restorer.exceptions.BlankIdFieldException
import software.galaniberico.restorer.exceptions.DataTypeMismatchException
import software.galaniberico.restorer.exceptions.UnexpectedFunctionCallException

object Restorer {

    fun with(id: String, value: Any?) {
        if (id.isBlank()) throw BlankIdFieldException("The id field cannot be blank. Please revise the parameter value")
        RestoreData.restoreIncome?.set(id, value)
            ?: throw UnexpectedFunctionCallException("You cannot add data outside of a OnSave function. This method should only be called inside a method annotated with the @OnSave tag.")
    }

    inline fun <reified T : Any?> get(id: String, default: T? = null): T? {
        if (id.isBlank()) throw BlankIdFieldException("The id field cannot be blank. Please revise the parameter value.")
        val (value, found) = RestoreDataActions.get(id)
        if (!found) return default
        if (value == null) return null
        if (value !is T) throw DataTypeMismatchException("The retrieved data for id \"$id\" is not of the expected type.")
        return value
    }
}