<template>
  <q-page class="q-pa-lg">
    <div class="text-h4 text-weight-bold text-primary q-mb-lg">Buscar Producto</div>

    <q-card class="q-pa-lg shadow-3 rounded-borders">
      <q-form ref="formRef">
        <q-input
          v-model="productId"
          label="Product ID"
          outlined
          dense
          color="primary"
          :rules="[rules.required, rules.numberOnly]"
        />

        <q-btn
          label="Buscar"
          color="primary"
          icon="search"
          rounded
          class="full-width q-mt-md"
          @click="getProduct"
        />
      </q-form>

      <q-banner v-if="product" class="q-mt-lg bg-grey-2 rounded-borders">
        <div><b>Nombre:</b> {{ product.name }}</div>
        <div><b>Precio:</b> {{ product.price }}</div>
        <div><b>Descripción:</b> {{ product.description }}</div>
      </q-banner>
    </q-card>
  </q-page>
</template>

<script setup>
import { ref } from 'vue'
import { productApi } from '../services/api'
import { useQuasar } from 'quasar'
import { rules } from 'src/utils/validationRules'

const $q = useQuasar()
const formRef = ref(null)

const productId = ref('')
const product = ref(null)

const getProduct = async () => {
  const valid = await formRef.value.validate()
  if (!valid) return

  try {
    const res = await productApi.get(`/products/${productId.value}`)

    product.value = {
      name: res.data.data.attributes.name,
      price: res.data.data.attributes.price,
      description: res.data.data.attributes.description,
    }
  } catch (err) {
    $q.notify({
      type: 'negative',
      message: err.response?.data?.errors?.[0]?.detail || 'Error',
    })
  }
}
</script>
