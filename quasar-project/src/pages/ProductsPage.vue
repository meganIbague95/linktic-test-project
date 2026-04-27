<template>
  <q-page class="q-pa-lg flex flex-center">
    <div class="page-container">
      <q-card class="q-pa-xl shadow-4">
        <div class="text-h5 text-weight-bold text-primary q-mb-lg">Crear Producto</div>

        <q-form ref="formRef">
          <q-input
            v-model="product.name"
            label="Nombre"
            outlined
            bg-color="white"
            color="primary"
            class="q-mb-md"
            :rules="[rules.required, rules.lettersOnly]"
          />

          <q-input
            v-model="product.price"
            label="Precio"
            outlined
            bg-color="white"
            type="number"
            prefix="$"
            color="primary"
            class="q-mb-md"
            :rules="[rules.required, rules.decimalNumber, rules.positiveNumber]"
          />

          <q-input
            v-model="product.description"
            label="Descripción"
            outlined
            bg-color="white"
            color="primary"
            class="q-mb-md"
            :rules="[rules.required, rules.noSpecialChars]"
          />

          <q-btn
            label="Crear Producto"
            color="primary"
            icon="add"
            rounded
            unelevated
            size="lg"
            class="full-width q-mt-md"
            @click="createProduct"
          />
        </q-form>
      </q-card>
    </div>
  </q-page>
</template>

<script setup>
import { ref } from 'vue'
import { productApi } from '../services/api'
import { useQuasar } from 'quasar'
import { useRouter } from 'vue-router'
import { rules } from 'src/utils/validationRules'

const $q = useQuasar()
const router = useRouter()
const formRef = ref(null)

const product = ref({
  name: '',
  price: '',
  description: '',
})

const createProduct = async () => {
  const valid = await formRef.value.validate()
  if (!valid) return

  try {
    const res = await productApi.post('/products', product.value)

    $q.notify({
      type: 'positive',
      message: 'Producto creado ID: ' + res.data.data.id,
    })

    setTimeout(() => router.push('/products-list'), 800)
  } catch (err) {
    $q.notify({
      type: 'negative',
      message: err.response?.data?.errors?.[0]?.detail || 'Error',
    })
  }
}
</script>
