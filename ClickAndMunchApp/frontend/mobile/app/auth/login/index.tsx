import { View, Text, KeyboardAvoidingView, ScrollView, useWindowDimensions, TextInput } from 'react-native'
import React from 'react'
import { ThemedText } from '@/presentation/theme/components/themed-text'
import ThemedTextInput from '@/presentation/theme/components/themed-text-input';

const LoginScreen = () => {

  const { height } = useWindowDimensions();

  return (

    <KeyboardAvoidingView behavior='padding' style={{ flex: 1 }}>
      <ScrollView
        style={{
          paddingHorizontal: 40,
        }}
      >
        <View
          style={{
            paddingTop: height * 0.35,
          }}
        >
          <ThemedText type='title'>Ingresar</ThemedText>
          <ThemedText style={{ color: 'grey', paddingTop: 10, paddingBottom: 20 }}>Por favor ingrese para continuar</ThemedText>
        </View>

        
        <View>
          <ThemedTextInput
            placeholder='Correo electronico'
            keyboardType='email-address'
            autoCapitalize='none'
            icon='mail-outline'
          />
          
          <ThemedTextInput
            placeholder='Contraseña'
            secureTextEntry
            autoCapitalize='none'
            icon='lock-closed-outline'
          />
        </View>

      </ScrollView>

    </KeyboardAvoidingView>
  )
}

export default LoginScreen