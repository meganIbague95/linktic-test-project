<template>
  <q-page class="q-pa-lg flex flex-center">
    <div class="page-container">
      <q-card class="q-pa-lg shadow-4">
        <!-- TÍTULO -->
        <div class="text-h5 text-weight-bold text-primary q-mb-lg">Inventario</div>

        <!-- 🔍 BUSCADOR -->
        <q-form ref="formRef" class="q-mb-md row q-col-gutter-sm">
          <div class="col-12 col-md-8">
            <q-input
              v-model="search"
              label="Buscar por nombre"
              outlined
              bg-color="white"
              color="primary"
              clearable
              :rules="[rules.lettersOnly]"
            />
          </div>

          <div class="col-12 col-md-4">
            <q-btn
              label="Buscar"
              icon="search"
              color="primary"
              rounded
              unelevated
              class="full-width"
              @click="filterInventory"
            />
          </div>
        </q-form>

        <!-- LOADING -->
        <div v-if="loading" class="flex flex-center q-pa-xl">
          <q-spinner color="primary" size="40px" />
        </div>

        <!-- TABLA -->
        <q-table
          v-else
          :rows="filteredInventory"
          :columns="columns"
          row-key="productId"
          flat
          bordered
          separator="horizontal"
          class="rounded-borders"
        >
          <template v-slot:no-data>
            <div class="text-grey text-center q-pa-md">No hay datos de inventario</div>
          </template>
        </q-table>
      </q-card>
    </div>
  </q-page>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { inventoryApi } from '../services/api'
import { rules } from 'src/utils/validationRules'
const inventory = ref([])
const filteredInventory = ref([])
const columns = [
  { name: 'productId', label: 'ID', field: 'productId' },
  { name: 'name', label: 'Nombre', field: 'name' },
  { name: 'price', label: 'Precio', field: 'price' },
  { name: 'quantity', label: 'Stock', field: 'quantity' },
]

onMounted(async () => {
  try {
    const res = await inventoryApi.get('/inventory')

    const data = res.data || []

    inventory.value = data.map((item) => ({
      productId: item.product?.id,
      name: item.product?.name || '',
      price: item.product?.price || 0,
      quantity: item.quantity || 0,
    }))

    // ✅ ESTA LÍNEA TE FALTA
    filteredInventory.value = inventory.value
  } catch (err) {
    console.error(err)
  }
})

const search = ref('')
const formRef = ref(null)

const filterInventory = async () => {
  // Validar input
  if (formRef.value) {
    const valid = await formRef.value.validate()
    if (!valid) return
  }

  // Si no hay texto → mostrar todo
  if (!search.value) {
    filteredInventory.value = inventory.value
    return
  }

  // Filtrar por nombre
  filteredInventory.value = inventory.value.filter((item) =>
    item.name.toLowerCase().includes(search.value.toLowerCase()),
  )
}
</script>
