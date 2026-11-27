package com.example

import com.example.ui.PifraDestination
import com.example.ui.PifraViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun `enterPortal navigates to official DDOALV url`() {
    val viewModel = PifraViewModel()
    assertEquals(PifraDestination.Home, viewModel.uiState.value.destination)

    viewModel.enterPortal()
    assertEquals(
      PifraDestination.Portal("https://m.pifra.gov.pk/DDOALV"),
      viewModel.uiState.value.destination,
    )
    assertEquals("https://m.pifra.gov.pk/DDOALV", viewModel.uiState.value.currentUrl)
    assertTrue(viewModel.uiState.value.isLoading)
  }

  @Test
  fun `guide and error recovery update state properly`() {
    val viewModel = PifraViewModel()

    viewModel.openGuide()
    assertEquals(PifraDestination.Guide, viewModel.uiState.value.destination)

    viewModel.enterPortal()
    viewModel.onWebError("Network error")
    assertEquals("Network error", viewModel.uiState.value.errorMessage)
    assertFalse(viewModel.uiState.value.isLoading)

    viewModel.triggerReload()
    assertNull(viewModel.uiState.value.errorMessage)
    assertTrue(viewModel.uiState.value.isLoading)

    viewModel.navigateHome()
    assertEquals(PifraDestination.Home, viewModel.uiState.value.destination)
  }
}
