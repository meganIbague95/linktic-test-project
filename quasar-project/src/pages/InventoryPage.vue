<template>
  <q-page class="q-pa-lg flex flex-center">
    <div class="page-container">
      <q-card class="q-pa-xl shadow-4">
        <div class="text-h5 text-weight-bold text-primary q-mb-lg">Consultar Inventario</div>

        <q-form ref="formRef">
          <q-input
            v-model="productId"
            label="Product ID"
            outlined
            bg-color="white"
            color="primary"
            class="q-mb-md"
            :rules="[rules.required, rules.numberOnly]"
          />

          <q-btn
            label="Consultar"
            color="primary"
            icon="search"
            rounded
            size="lg"
            class="full-width q-mt-md"
            @click="getInventory"
          />
        </q-form>

        <!-- RESULTADO -->
        <q-banner v-if="inventory" class="q-mt-lg bg-grey-2 rounded-borders">
          <div><b>Producto:</b> {{ inventory.name }}</div>
          <div><b>Precio:</b> $ {{ inventory.price }}</div>
          <div><b>Stock:</b> {{ inventory.quantity }}</div>
        </q-banner>
      </q-card>
    </div>
  </q-page>
</template>

<script setup>
import { ref } from 'vue'
import { inventoryApi } from '../services/api'
import { useQuasar } from 'quasar'
import { rules } from 'src/utils/validationRules'

const $q = useQuasar()
const formRef = ref(null)

const productId = ref('')
const inventory = ref(null)

const getInventory = async () => {
  const valid = await formRef.value.validate()
  if (!valid) return

  try {
    const res = await inventoryApi.get(`/inventory/${productId.value}`)

    inventory.value = {
      name: res.data.product.name,
      price: res.data.product.price,
      quantity: res.data.quantity,
    }
  } catch (err) {
    $q.notify({
      type: 'negative',
      message: err.response?.data?.errors?.[0]?.detail || 'Error',
    })
  }
}
</script>
