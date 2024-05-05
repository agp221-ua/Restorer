package software.galaniberico.restorer.exceptions

import android.util.Log
import software.galaniberico.restorer.configuration.PLUGIN_LOG_TAG
import java.lang.IllegalArgumentException


class DataTypeMismatchException : IllegalStateException {
    constructor() : super()
    constructor(message: String?) : super(message){
        if(message != null) Log.e(PLUGIN_LOG_TAG, message)
    }
    constructor(message: String?, cause: Throwable?) : super(message, cause){
        if(message != null) Log.e(PLUGIN_LOG_TAG, message)
    }
    constructor(cause: Throwable?) : super(cause)
}
class BlankIdFieldException : IllegalArgumentException {
    constructor() : super()
    constructor(message: String?) : super(message) {
        if (message != null) Log.e(PLUGIN_LOG_TAG, message)
    }

    constructor(message: String?, cause: Throwable?) : super(message, cause) {
        if (message != null) Log.e(PLUGIN_LOG_TAG, message)
    }

    constructor(cause: Throwable?) : super(cause)
}
class UnexpectedFunctionCallException : IllegalStateException {
    constructor() : super()
    constructor(message: String?) : super(message){
        if(message != null) Log.e(PLUGIN_LOG_TAG, message)
    }
    constructor(message: String?, cause: Throwable?) : super(message, cause){
        if(message != null) Log.e(PLUGIN_LOG_TAG, message)
    }
    constructor(cause: Throwable?) : super(cause)
}
