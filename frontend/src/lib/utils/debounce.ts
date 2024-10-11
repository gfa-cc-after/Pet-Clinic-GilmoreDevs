function debounce<
  ParamsT,
  ReturnT,
  F extends (...args: ParamsT[]) => Promise<ReturnT>,
>(func: F, wait: number) {
  let timeoutId: NodeJS.Timeout | null = null;
  return (...args: Parameters<F>): Promise<ReturnType<F>> => {
    return new Promise((resolve, reject) => {
      if (timeoutId) {
        clearTimeout(timeoutId);
      }
      timeoutId = setTimeout(async () => {
        try {
          const result = (await func(...args)) as ReturnType<F>;
          resolve(result);
        } catch (error) {
          reject(error);
        }
      }, wait);
    });
  };
}

export { debounce };
