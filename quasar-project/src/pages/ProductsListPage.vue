<template>
  <q-page class="q-pa-lg flex flex-center">
    <div class="page-container">
      <q-card class="q-pa-lg shadow-4">
        <div class="text-h5 text-weight-bold text-primary q-mb-lg">Productos</div>

        <q-table
          :rows="products"
          :columns="columns"
          row-key="id"
          flat
          bordered
          separator="horizontal"
          class="rounded-borders"
        />
      </q-card>
    </div>
  </q-page>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { productApi } from '../services/api'

const products = ref([])

const columns = [
  { name: 'id', label: 'ID', field: 'id' },
  { name: 'name', label: 'Nombre', field: 'name' },
  { name: 'price', label: 'Precio', field: 'price' },
  { name: 'description', label: 'Descripción', field: 'description' },
]

onMounted(async () => {
  const res = await productApi.get('/products')

  products.value = res.data.data.map((p) => ({
    id: p.id,
    name: p.attributes.name,
    price: p.attributes.price,
    description: p.attributes.description,
  }))
})
</script>
