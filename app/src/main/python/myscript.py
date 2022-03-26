import io
import matplotlib.pyplot as plt
import numpy as np
import copy

def plot(xx, yy):
    # xa = [float(word) for word in x.split()]
    # ya = [float(word) for word in y.split()]
    #
    # fig, ax = plt.subplots()
    # ax.plot(xa, ya)

    # Define X and Y variable data
    # x = np.array([1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
    # y = np.array([1027, 1030, 1041, 1047, 1057, 1066, 1073, 1088, 1099, 1099])

    xx_list = []
    for n in range(len(xx)):
      xx[n] = xx[n]
      xx_list.append(xx[n])

    yy_list = []
    for n in range(len(yy)):
        yy[n] = yy[n]
        yy_list.append(yy[n])




    x = np.array(xx_list)
    y = np.array(yy_list)
    plt.plot(x, y)
    plt.xlabel("X-axis")  # add X-axis label

    plt.ylabel("Y-axis")  # add Y-axis label
    plt.title("Any suitable title")  # add title



    f = io.BytesIO()
    plt.savefig(f, format="png")
    return f.getvalue()