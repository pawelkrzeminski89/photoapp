package com.example.photoapp.usecases


import com.example.photoapp.local.PixabayImageEntity
import com.example.photoapp.model.PixabaySearchArguments
import com.example.photoapp.remote.NetworkResult
import com.example.photoapp.remote.PixabayApiResponse
import com.example.photoapp.remote.PixabayHitResponse
import com.example.photoapp.repository.Repository
import junit.framework.TestCase
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

internal class GetPixabayImageTest : TestCase() {
    

    @Mock
    lateinit var repository: Repository

    lateinit var getPixabayImage: GetPixabayImage

    @Mock
    lateinit var pixabaySearchArguments: PixabaySearchArguments

    @Before
    public override fun setUp() {
        repository = Mockito.mock(Repository::class.java)
        pixabaySearchArguments = Mockito.mock(PixabaySearchArguments::class.java)
        getPixabayImage = GetPixabayImage(repository)
        super.setUp()
    }

    @Test
    fun testGetPixabayImagesFromApi() = runBlocking {

        var exampleHit = PixabayHitResponse(1, "", "", "", 1, 1, 1, 1, "")
        var exampleApiReponse = PixabayApiResponse(listOf(exampleHit, exampleHit))

        Mockito.`when`(pixabaySearchArguments.preparePixabayApiQueryArgument()).thenReturn("fruit")
        Mockito.`when`(repository.getPixabayImages(pixabaySearchArguments))
            .thenReturn(
                NetworkResult.Success(exampleApiReponse)
            )
        val flowResult = getPixabayImage(pixabaySearchArguments).toList()

        assertEquals(2, flowResult.size)
        assertEquals(NetworkResult.Loading()::class.java, flowResult.first()::class.java)
        assertEquals(
            NetworkResult.Success(exampleApiReponse)::class.java,
            flowResult.last()::class.java
        )
        assertEquals(flowResult.last().data!!.hits.size, 2)


    }

    @Test
    fun testLoadingCacheWhenResultFromApiIsError() = runBlocking {

        var exampleHitEntity = PixabayImageEntity(1, "", "", "", 1, 1, 1, 1, "", "")
        var exampleLocalDataResponse = listOf(exampleHitEntity, exampleHitEntity, exampleHitEntity)
        Mockito.`when`(repository.getPixabayImages(pixabaySearchArguments))
            .thenReturn(
                NetworkResult.Error("error")
            )
        Mockito.`when`(repository.getPixabayImageByQuery(pixabaySearchArguments.preparePixabayApiQueryArgument()))
            .thenReturn(exampleLocalDataResponse)
        val flowResult = getPixabayImage(pixabaySearchArguments).toList()

        assertEquals(2, flowResult.size)
        assertEquals(NetworkResult.Loading()::class.java, flowResult.first()::class.java)
        assertEquals(
            NetworkResult.Success<Any>(exampleLocalDataResponse)::class.java,
            flowResult.last()::class.java
        )
        assertEquals(flowResult.last().data!!.hits.size, 3)

    }

    @Test
    fun testExtensionWhenApiResultIsErrorAndCacheIsEmpty() = runBlocking {


        var exampleLocalDataResponse = listOf<PixabayImageEntity>()
        Mockito.`when`(repository.getPixabayImages(pixabaySearchArguments))
            .thenReturn(
                NetworkResult.Error("error")
            )
        Mockito.`when`(repository.getPixabayImageByQuery(pixabaySearchArguments.preparePixabayApiQueryArgument()))
            .thenReturn(exampleLocalDataResponse)
        val flowResult = getPixabayImage(pixabaySearchArguments).toList()

        assertEquals(2, flowResult.size)
        assertEquals(NetworkResult.Loading()::class.java, flowResult.first()::class.java)
        assertEquals(NetworkResult.Error::class.java, flowResult.last()::class.java)

    }

    @Test
    fun testGetPixabayImagesFromApiAndSaveCache() = runBlocking {

        var exampleHit = PixabayHitResponse(1, "", "", "", 1, 1, 1, 1, "")
        var exampleApiReponse = PixabayApiResponse(listOf(exampleHit, exampleHit))

        Mockito.`when`(pixabaySearchArguments.preparePixabayApiQueryArgument()).thenReturn("fruit")
        Mockito.`when`(repository.getPixabayImages(pixabaySearchArguments))
            .thenReturn(
                NetworkResult.Success(exampleApiReponse)
            )
        val flowResult = getPixabayImage(pixabaySearchArguments).toList()

        assertEquals(2, flowResult.size)
        assertEquals(NetworkResult.Loading()::class.java, flowResult.first()::class.java)
        assertEquals(
            NetworkResult.Success(exampleApiReponse)::class.java,
            flowResult.last()::class.java
        )
        assertEquals(flowResult.last().data!!.hits.size, 2)
        Mockito.verify(repository, Mockito.times(1))
            .insertAllPixabayImageEntity(exampleApiReponse.hits.map {
                PixabayImageEntity.FromPixabayHitResponse(
                    it,
                    pixabaySearchArguments.preparePixabayApiQueryArgument()
                )
            });

    }
}