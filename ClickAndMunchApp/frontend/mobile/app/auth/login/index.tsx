import { View, Text, KeyboardAvoidingView, ScrollView, useWindowDimensions, Alert } from 'react-native'
import React, { useState } from 'react'
import { ThemedText } from '@/presentation/theme/components/themed-text'
import ThemedTextInput from '@/presentation/theme/components/themed-text-input';
import ThemedButton from '@/presentation/theme/components/themed-button';
import ThemedLink from '@/presentation/theme/components/themed-link';
import { authLogin } from '@/core/auth/actions/auth-actions';
import { useRouter } from 'expo-router';

const LoginScreen = () => {

  const { height } = useWindowDimensions();
  const router = useRouter();

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);

  const onSubmit = async () => {
    if (!username || !password) {
      Alert.alert('Error', 'Por favor ingrese usuario y contraseña');
      return;
    }

    setLoading(true);
    const res = await authLogin(username.trim(), password);
    setLoading(false);

    if (!res) {
      Alert.alert('Error', 'Usuario o contraseña incorrectos');
      return;
    }

    router.push('/');
  }

  return (

    <KeyboardAvoidingView behavior='padding' style={{ flex: 1 }}>
      <ScrollView
        style={{
          paddingHorizontal: 40,
        }}
      >
        <View
          style={{
            paddingTop: height * 0.12,
          }}
        >
          <ThemedText type='title'>Ingresar</ThemedText>
          <ThemedText style={{ color: 'grey', paddingTop: 10, paddingBottom: 20 }}>Por favor ingrese para continuar</ThemedText>
        </View>

        <View>
          <ThemedTextInput
            placeholder='Nombre de usuario'
            autoCapitalize='none'
            icon='person-outline'
            value={username}
            onChangeText={setUsername}
          />

          <ThemedTextInput
            placeholder='Contraseña'
            secureTextEntry
            autoCapitalize='none'
            icon='lock-closed-outline'
            value={password}
            onChangeText={setPassword}
          />
        </View>

        <View style={{ marginTop: 10 }} />

        <ThemedButton onPress={onSubmit}>{loading ? 'Ingresando...' : 'Ingresar'}</ThemedButton>

        <View style={{ marginTop: 50 }} />

        <View style={{
          flexDirection: 'row',
          justifyContent: 'center',
          alignItems: 'center'
        }}>
            <ThemedText style={{marginHorizontal: 10}}>No tienes cuenta?</ThemedText>
            <ThemedLink href='/auth/register' >
              Crear cuenta
            </ThemedLink>

        </View>

      </ScrollView>

    </KeyboardAvoidingView>
  )
}

export default LoginScreen