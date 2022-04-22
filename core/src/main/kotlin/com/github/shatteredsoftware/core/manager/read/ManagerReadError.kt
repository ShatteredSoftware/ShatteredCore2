package com.github.shatteredsoftware.core.manager.read

class ManagerReadError(val message: String, val type: ReadErrorType) {
        enum class ReadErrorType {
            /** When asking for a value that does not exist */
            NotFound,
            /** When reading from a local source fails due to an IO Error */
            IOError,
            /** When reading from a remote source that fails to connect */
            ConnectionError,
            /** When trying to access a file fails due to permissions */
            PermissionError
        }
}