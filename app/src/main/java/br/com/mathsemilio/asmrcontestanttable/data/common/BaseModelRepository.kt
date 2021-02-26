package br.com.mathsemilio.asmrcontestanttable.data.common

abstract class BaseModelRepository<Model, Dao : BaseDAO<Model>> {

    private lateinit var _dao: Dao
    var dao: Dao
        get() = _dao
        set(value) {
            _dao = value
        }

    suspend fun insertData(data: Model) = _dao.insertData(data)

    suspend fun updateData(data: Model) = _dao.updateData(data)
}