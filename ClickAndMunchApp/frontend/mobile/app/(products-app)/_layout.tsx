import { View, Text, ActivityIndicator } from 'react-native'
import React from 'react'
import { useAuthStore } from '@/presentation/auth/store/useAuthStore'
import { useEffect } from 'react';

const CheckAuthenticationLayout = () => {

    const { status, checkStatus } = useAuthStore();

    useEffect(() => {
        checkStatus();
    }, [])

    if (status === 'checking') {
        return (
            <View
                style={{
                    flex: 1,
                    justifyContent: 'center',
                    alignItems: 'center',
                    marginBottom: 5
                }}
            >
                <ActivityIndicator/>
            </View>
        )
    }

    
}

export default CheckAuthenticationLayout