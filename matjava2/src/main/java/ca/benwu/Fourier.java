package ca.benwu;

import ca.benwu.number.ComplexNumber;

/**
 * Created by Ben Wu on 2016-11-25.
 */

public class Fourier {

    public static ComplexNumber rootOfUnity(int n) {
        return new ComplexNumber(Math.cos(2*Math.PI/n), Math.sin(2*Math.PI/n));
    }

    // only ffts the first row
    public static Matrix dft(Matrix f) {
        ComplexNumber[] F = fft(f.getRow(0).getValueArray()[0]);

        return new Matrix(new ComplexNumber[][] {F});
    }

    // x.length must be a power of 2
    public static ComplexNumber[] fft(ComplexNumber[] f) {
        int n = f.length;

        if (n == 1) return new ComplexNumber[] { f[0] };

        ComplexNumber[] even = new ComplexNumber[n/2];
        ComplexNumber[] odd  = new ComplexNumber[n/2];
        for (int k = 0; k < n/2; k++) {
            even[k] = f[2*k];
            odd[k] = f[2*k + 1];
        }
        even = fft(even);
        odd = fft(odd);

        ComplexNumber[] F = new ComplexNumber[n];
        for (int k = 0; k < n/2; k++) {
            double kth = -2 * k * Math.PI / n;
            ComplexNumber wk = new ComplexNumber(Math.cos(kth), Math.sin(kth));
            F[k] = even[k].plus(wk.times(odd[k]));
            F[k+n/2] = even[k].minus(wk.times(odd[k]));
        }
        return F;
    }

    public static Matrix idft(Matrix F) {
        ComplexNumber[] f = ifft(F.getRow(0).getValueArray()[0]);

        return new Matrix(new ComplexNumber[][] {f});
    }

    // x.length must be a power of 2
    public static ComplexNumber[] ifft(ComplexNumber[] F) {
        int n = F.length;

        ComplexNumber[] f = new ComplexNumber[n];
        for (int i = 0; i < n; i++) {
            f[i] = F[i].conjugate();
        }
        f = fft(f);
        for (int i = 0; i < n; i++) {
            f[i] = f[i].conjugate().times(1.0 / n);
        }

        return f;
    }

}
