<template>
  <q-page class="q-pa-lg flex flex-center">
    <div class="page-container">
      <q-card class="q-pa-xl shadow-4">
        <div class="text-h5 text-weight-bold text-primary q-mb-lg">Actualizar Stock</div>

        <q-form ref="formRef">
          <q-input
            v-model="productId"
            label="Product ID"
            outlined
            bg-color="white"
            :rules="[rules.required, rules.numberOnly]"
          />

          <q-input
            v-model="quantity"
            label="Cantidad"
            outlined
            bg-color="white"
            :rules="[rules.required, rules.numberOnly, rules.positiveNumber]"
          />

          <q-btn
            label="Actualizar"
            color="secondary"
            icon="update"
            rounded
            size="lg"
            class="full-width q-mt-md"
            @click="updateStock"
          />
        </q-form>
      </q-card>
    </div>
  </q-page>
</template>

<script setup>
import { ref } from 'vue'
import { inventoryApi } from '../services/api'
import { useQuasar } from 'quasar'
import { useRouter } from 'vue-router'
import { rules } from 'src/utils/validationRules'

const $q = useQuasar()
const router = useRouter()
const formRef = ref(null)

const productId = ref('')
const quantity = ref('')

const updateStock = async () => {
  const valid = await formRef.value.validate()
  if (!valid) return

  try {
    await inventoryApi.put(`/inventory/${productId.value}?quantity=${quantity.value}`)

    $q.notify({ type: 'positive', message: 'Stock actualizado' })

    setTimeout(() => router.push('/inventory-list'), 800)
  } catch (err) {
    $q.notify({
      type: 'negative',
      message: err.response?.data?.errors?.[0]?.detail || 'Error',
    })
  }
}
</script>
