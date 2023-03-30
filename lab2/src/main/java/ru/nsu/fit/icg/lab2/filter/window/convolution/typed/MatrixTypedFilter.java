package ru.nsu.fit.icg.lab2.filter.window.convolution.typed;

import ru.nsu.fit.icg.lab2.filter.window.convolution.ConvolutionFilter;

public abstract class MatrixTypedFilter extends ConvolutionFilter {

    protected MatrixType matrixType;

    public MatrixTypedFilter(MatrixType matrixType) {
        setMatrixType(matrixType);
    }

    public void setMatrixType(MatrixType matrixType) {
        this.matrixType = matrixType;
        matrix = matrixType.getMatrix();
    }

    public MatrixType getMatrixType() {
        return matrixType;
    }

    public abstract MatrixType[] getMatrixTypes();

    @Override
    public String getJsonName() {
        return "typed";
    }

    public abstract String getMatrixTypeName();
}
