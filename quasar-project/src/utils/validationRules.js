export const rules = {
  required: (val) => !!val || 'Campo obligatorio',

  positiveNumber: (val) => val >= 0 || 'Debe ser un número positivo',

  numberOnly: (val) => /^\d+$/.test(val) || 'Solo números',

  decimalNumber: (val) => /^\d+(\.\d+)?$/.test(val) || 'Número inválido',

  lettersOnly: (val) => /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/.test(val) || 'Solo letras',

  noSpecialChars: (val) => /^[a-zA-Z0-9\s]+$/.test(val) || 'No caracteres especiales',
}
